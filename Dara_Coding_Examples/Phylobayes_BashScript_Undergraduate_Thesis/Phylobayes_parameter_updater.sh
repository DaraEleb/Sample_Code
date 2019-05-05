// Author : Oluwadara Elebute

#!/bin/bash

i=$1
    
while [ $i -lt  21 ]; do

    spr=$i
    nni=$2
    fname1=$3'-'$i
    fname2=$4'-'$i
    direcname=$5'-'$i
    tree=$6

    > $fname1
        
    > $fname2

// Creating the script to run Phylobayes MPI
    
    echo 'Creating Phylobayes Script'
        
        printf '#!/bin/bash' >> $fname1

        printf '#!/bin/bash' >> $fname2
    
        printf '' >> $fname1

        printf '' >> $fname2
        
        printf '\nmpirun -np 16 ../pb_mpi -d '$tree' -gtr -ncat 1 -spr '$spr' -nni '$nni' run1_'$i'\n' >> $fname1
        
        printf '\nmpirun -np 16 ../pb_mpi -d '$tree' -gtr -ncat 1 -spr '$spr' -nni '$nni' run2_'$i'\n' >> $fname2

    echo 'creating executable'
       
        chmod "a+x" $fname1
        
        chmod "a+x" $fname2

       echo 'passing to the queue'
        
        qsub -q all.q@compute-0-9.local -cwd -pe orte 16 $fname1
        
        qsub -q all.q@compute-0-9.local -pe orte 16 -cwd $fname2

    // Storing results in result1 file

    printf '\n'$spr'\t'$nni >> result1

    sleep 100

    echo 'calling toptest.sh'
    
    one='run1_'$i
    two='run2_'$i

    ./toptest.sh 0.1 $one $two 

    echo 'starting avgcompt.sh'

    ./avgcompt 5 $one $two

//copy files into a new directory

    mkdir $direcname
    
      cp $fname1.* $direcname

      cp $fname2.* $direcname

    sleep 100

    mv run* $direcname

    i=$((i+3))

done

 
Test Script (Convergence Testing)

#!/bin/bash

req=$1

ffile=$2

sfile=$3

// obtaining the value of run file

var1=`cat $ffile.run`
var2=`cat $sfile.run`

while [ $var1 == 1 ] && [ $var2 == 1 ]; do

// starting bpcomp

     ./bpsesh $ffile $sfile

// collecting value of maxdiff
   
        val=$(awk '{ print $3 }' bpcomp.bpdiff | head -2| tail -1)
          
             if (( $(bc <<< "$val <= $req") == 1 )); then
                echo 'in the if statement'
                       printf "0" > $ffile.run
                        printf "0" > $sfile.run
                        var1=`cat $ffile.run`
                        var2=`cat $sfile.run`
                fi
done

#!/bin/bash

echo 'last step'

tracker=$1
ttracker="-$tracker"
ffile="$2.trace"
sfile="$3.trace"

next=1

val1=$(awk '{ print $2 }' $ffile | head $ttracker | tail -1)
sleep 3

cycnum1=$(awk '{ print $1 }' $ffile | tail -1)
accycnum1=$(($cycnum1 - ($tracker-1)))


while [ $tracker -gt 0 ]; do
    if [ $tracker -eq $cycnum1 ]; then
        tracker=0
    else
    tracker=$(($tracker+1))
    ttracker="-$tracker"
    next=$(awk '{ print $2 }' $ffile | head $ttracker | tail -1)
    val1=`echo $val1 $next | awk '{print $1 + $2}'`
    fi
    
done

tracker=$1
ttracker="-$tracker"

val2=$(awk '{ print $2 }' $sfile | head $ttracker | tail -1)
cycnum2=$(awk '{ print $1 }' $sfile | tail -1)
accycnum2=$(($cycnum2 - ($tracker-1)))

while [ $tracker -gt 0 ]; do
    if [ $tracker == $cycnum2 ]; then
        tracker=0
    else
    tracker=$(($tracker+1))





