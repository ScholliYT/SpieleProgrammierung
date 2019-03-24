#!/bin/bash
if [ -n "$CHECKPROJECTSDIR" ]; then #check if envvar is empty
	echo "checking for all projects having a travis build set up"
	PASS=1
	for d in $CHECKPROJECTSDIR/*/ ; do
		echo "checking "$d"..."
		if grep -q $d .travis.yml; then
			echo "project found in travis.yml!"
		else
			echo "project "$d" not found in travis.yml. Please configure this project to make this test pass!"
			PASS=0
		fi
	done
	if [ "$PASS" = 1 ]; then
		echo "=========> all projects are set up!"
	else
		echo "=========> not all projects are set up!"
		exit 1
	fi
else
	echo "not checking for projects having a travis build set up this time"
fi
