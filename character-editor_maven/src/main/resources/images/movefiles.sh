#!/bin/bash
FOLDER=body_parts/
for D in `find $FOLDER -type d ! -path $FOLDER`; do
  D_NAME="${D##*/}"
  for F in `find $D -maxdepth 1 ! -type d`; do
    F_NAME="${F##*/}"
    mv $F "$FOLDER$D_NAME""_""$F_NAME"
  done
done
