import os, sys, string
import optparse
import apollo
import datetime 

if __name__ == '__main__':
    parser = optparse.OptionParser(usage="""
    %prog [--help][-r run_id][-s status][-m message]
    -r or --runID   The apollo runId
    -s or --status   The apollo status
    -m or --message The apollo message to correspond to the status
    """)
    
    parser.add_option("-r", "--runId", type="string",
                      help="The Apollo RunId")
    # ## STB make this an options list
    parser.add_option("-s", "--status", type="string",
                      help="The status to send to the database")
    parser.add_option("-m", "--message", type="string",
                      help="The message to send to the database")
    parser.add_option("-H", "--dbHost", type="string", default="warhol-fred.psc.edu")
    parser.add_option("-D", "--dbName", type="string", default="test")
    parser.add_option("-U", "--dbUser", type="string", default="apolloext")
    parser.add_option("-P", "--pword", type="string")

    opts, args = parser.parse_args()
    try: 
        DBHosts = opts.dbHost
    	apolloDB = apollo.ApolloDB(host_=DBHosts, dbname_=opts.dbName, user_=opts.dbUser, password_=opts.pword)
    	apolloDB.connect()
    # ## Add timestamp to this.
    	d = datetime.datetime.now()
    	thisMessage = "%s: %s" % (d.strftime('%B %d, %Y %I:%M%p'), opts.message)
    	apolloDB.setRunStatus(opts.runId, opts.status, opts.message)
    except Exception as e:
        sys.stderr.write(str(e))
        sys.exit(1)
    
    
