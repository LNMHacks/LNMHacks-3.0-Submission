    clear;
    noise=0.1;
    [w,map]=gifread('lena.gif');
    x=ind2gray(w,map);
    filter1=d2gauss(n1,sigma1,n2,sigma2,theta);
    x_rand=noise*randn(size(x));
    y=x+x_rand;
    f1=conv2(x,filter1,'same');
    rf1=conv2(y,filter1,'same');
    figure(1);
    subplot(2,2,1);imagesc(x);
    subplot(2,2,2);imagesc(y);
    subplot(2,2,3);imagesc(f1);
    subplot(2,2,4);imagesc(rf1);
    colormap(gray);
    clear;
    Ts=1/1e3; 
    NA=10000;
    A=floor(rand(1,NA)+0.5);
    input=A;
    G=[1 0 0; 1 0 1; 1 1 1];
    k=1;
    N=length(channel_output);
    SNRdB= -10:5:20;
    SNR=10.^(SNRdB/10);
    Counter=zeros(1,length(SNRdB));
    loop=0;
    Fd=66; 
    fm=round(Fd*NA*Ts);
    xin=randn(1,(NA+2)*3);
    yin=randn(1,(NA+2)*3);
    for f=1fm-1)
    filter(f)=1.5/(pi*fm*sqrt(1-(f/fm)^2));
    end
    for f=(fm-1)NA+2)*3
    filter(f)=0;
    end
    xout=xin.*filter;
    yout=yin.*filter;
    xifft=real(ifft(xout));
    yifft=real(ifft(yout));
    std_x=std(xifft);
    std_y=std(yifft);
    xifft=xifft/std_x/sqrt(2);
    yifft=yifft/std_y/sqrt(2);
    rx=sqrt(xifft.^2+yifft.^2);
    loop=0;
    for b=sqrt(SNR)
    loop=loop+1;
    dfad=0;
    for n=1NA+2)*3 
    x=randn;
    t=channel_output(n);
    if t>=0.5
    a=b*rx(n);
    r=a+x;
    if r<0
    dfad=dfad+1; 
    Df(n)=0;
    else
    Df(n)=1;
    end
    else
    a=(-b)*rx(n);
    r=a+x;
    if r>0
    dfad=dfad+1;
    Df(n)=1;
    else
    Df(n)=0;
    end
    end
    Counterf(loop)=dfad; 
    end
    output=viterbi(G,k,Df); 
    zf=0;
    v=1;
    for v=v:1:length(A);
    if xor(input(1,v),output(1,v))==1
    zf=zf+1;
    end
    end
    Counter_wcf(loop)=zf;
    end
    Pef=Counterf/N;
    Pe1f=Counter_wcf/NA;
    save D1_66SNRdBPe1fPef
    clear;
    Ts=1/1e3; 
    NA=10000;
    A=floor(rand(1,NA)+0.5);
    input=A;
    G=[1 0 0; 1 0 1; 1 1 1];
    k=1;
    N=length(channel_output);
    SNRdB= -10:5:20;
    SNR=10.^(SNRdB/10);
    Counter=zeros(1,length(SNRdB));
    loop=0;