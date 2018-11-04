
while true
do
    $(python capture.py)
    a=`expr $a + 1`
    sleep 1s
done
