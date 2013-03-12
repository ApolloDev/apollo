import fred
from fredUtils import pollFredStatus
#run_length=100
#f = fred.FRED()
#fr = fred.FRED_RUN(f,"10test2")
#pop_count = [0.0] * run_length * 4
fredstat = pollFredStatus("227233")
#FREDAveOutput = fr.outputsAve
#fr.print_ave_outputs()
#fr.print_outputs(1)
#print str(FREDAveOutput[0].keys())
#print 'Length = ' + str(len(FREDAveOutput))

## for i in range(len(FREDAveOutput)):
##     print "i = " + str(i)
##     pop_count[i*4] = FREDAveOutput[i]['S']
##     pop_count[i*4+1] = FREDAveOutput[i]['E']
##     pop_count[i*4+2] = FREDAveOutput[i]['I']
##     pop_count[i*4+3] = FREDAveOutput[i]['R']
    
## for i in range(0,len(pop_count),4):
##     print "Day = %d S = %d E = %d I = %d R = %d"%(i/4,\
##                                                   pop_count[i],\
##                                                   pop_count[i+1],\
##                                                   pop_count[i+2],\
##                                                   pop_count[i+3])
