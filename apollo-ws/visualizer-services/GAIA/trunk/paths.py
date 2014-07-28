import sys,os,os.path
import visWS

try:
	sys.path.append(visWS.configuration['local']['apolloPyDir'])

except Exception as e:
	print 'The Directory for apolloPyDir '\
	    + '%s is not available, please check '%(visWS.configuration['local']['apolloPyDir'])\
	    + 'the path in visWS.py again'
	raise e


