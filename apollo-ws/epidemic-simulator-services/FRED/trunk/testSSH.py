import paramiko

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect('warhol.psc.edu',username='stbrown',password='',key_filename='id_rsa')
stdin,stdout,stderr=ssh.exec_command("echo $HOME")
remote_scratch_dir = stdout.read().strip()
print "remote = " + remote_scratch_dir + '/test'
##for line in stdout.readlines():
##    print line

transport = paramiko.Transport(('unicron.psc.edu',22))
privateKey = './id_rsa'
key = paramiko.RSAKey.from_private_key_file(privateKey)

transport.connect(username='stbrown',pkey = key)
sftp = paramiko.SFTPClient.from_transport(transport)
#sftp.mkdir(remote_scratch_dir + '/test_dir')
#sftp.set_missing_host_key_policy(paramiko.AutoAddPolicy())

#stdin,stdout,stderr=ssh.exec_command('cd ' + remote_scratch_dir + '/fred.tmp.42082;'\
#				     ' module load torque; qsub fred_submit.pbs')

#returnVal = stdout.read().split('.')[0]
#print "return = " + returnVal
#try:   
#    stdin,stdout,stderr=ssh.exec_command('qstat 227898')#+ str(returnVal))

##    print stdout.readlines()[len(stdout.readlines())-1].split()[4]
##except:
##    print 'No Shit in the queue'
#for line in stdout.readlines():
#    print line
