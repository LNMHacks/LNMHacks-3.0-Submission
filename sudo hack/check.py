import pandas as pd
'''import keras 
import glob 
import os
import pydicom as dcm
import matplotlib
from keras.preprocessing.image import ImageDataGenerator
from keras.models import load_model
import numpy as np
import keras_preprocessing.image as KPImage
from PIL import Image
#from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn import preprocessing
class_enc= preprocessing.LabelEncoder()
from glob import glob

path = "stage_1_test_images/"
datadir = 'stage_1_test_images/'
test_img_df = pd.DataFrame({'path': 
              glob(datadir+'*.dcm')})
test_img_df['patientId'] = test_img_df['path'].map(lambda x: os.path.splitext(os.path.basename(x))[0])



img_gen_args = dict(samplewise_center=False, 
                              samplewise_std_normalization=False, 
                              horizontal_flip = True, 
                              vertical_flip = False, 
                              height_shift_range = 0.05, 
                              width_shift_range = 0.02, 
                              rotation_range = 3, 
                              shear_range = 0.01,
                              fill_mode = 'nearest',
                              zoom_range = 0.05,
                               )
img_gen = ImageDataGenerator(**img_gen_args)




def flow_from_dataframe(img_data_gen, in_df, seed = None, **dflow_args):
    base_dir = os.path.dirname(in_df['path'].values[0])
    print('## Ignore next message from keras, values are replaced anyways: seed: {}'.format(seed))
    df_gen = img_data_gen.flow_from_directory(base_dir, 
                                     class_mode = 'sparse',
                                              seed = seed,
                                    **dflow_args)
    df_gen.filenames = in_df['path'].values
    df_gen.classes = np.stack(in_df['patientId'].values,0)
    df_gen.samples = in_df.shape[0]
    df_gen.n = in_df.shape[0]
    df_gen._set_index_array()
    df_gen.directory = '' # since we have the full path
    print('Reinserting dataframe: {} images'.format(in_df.shape[0]))
    return df_gen

new_model = keras.models.load_model('full_model.h5')

IMG_SIZE = (224,224)
BATCH_SIZE = 24

img = flow_from_dataframe(img_gen,test_img_df, 
                            target_size = IMG_SIZE,
                             color_mode = 'rgb',
                            batch_size = BATCH_SIZE)
							
							
new_model.compile(loss='binary_crossentropy',
              optimizer='rmsprop',
              metrics=['accuracy'])
			  
def read_dicom_image(path):
    img_arr = dcm.read_file(path).pixel_array
    return img_arr/img_arr.max()
    
class medical_pil():
    @staticmethod
    def open(path):
        if '.dcm' in path:
            c_slice = read_dicom_image(path)
            int_slice =  (255*c_slice).clip(0, 255).astype(np.uint8) # 8bit images are more friendly
            return Image.fromarray(int_slice)
        else:
            return Image.open(path)
    fromarray = Image.fromarray
KPImage.pil_image = medical_pil


from tqdm import tqdm
test_steps = 2*test_img_df.shape[0]//BATCH_SIZE
out_ids, out_vec = [], []
for _, (t_x, t_y) in zip(tqdm(range(test_steps)),img):
    out_vec += [new_model.predict(t_x)]
    out_ids += [t_y]
out_vec = np.concatenate(out_vec, 0)
out_ids = np.concatenate(out_ids, 0)'''


pred_avg_df = pd.read_csv('image_level_class_probs.csv')
pred_avg_df['PredictionString'] = pred_avg_df['Lung Opacity'].map(lambda x: ('%2.2f 0 0 1024 1024' % x) if x>0.5 else '')

pred_avg_df[['patientId', 'PredictionString']].to_csv('submission.csv', index=False)


