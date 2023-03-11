# Decompress files if necessary
if [[ -f Card\ Image.zip ]]
then
    tar -xf Card\ Image.zip
    rm -r Card\ Image.zip
fi
if [[ -f Nothing.zip ]]
then
    tar -xf Nothing.zip
    rm -r Nothing.zip
fi

# Compile and run BigTwo
javac BigTwo.java
java BigTwo