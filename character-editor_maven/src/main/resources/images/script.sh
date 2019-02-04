mkdir temp
for file in *.png
do
  mv "$file" "temp/${file,,}"
done
mv temp/* .
rm -rf temp
