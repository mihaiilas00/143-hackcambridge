from collections import deque
import os.path
from datetime import datetime
import time
from threading import Thread
from threading import Condition

Flag=0
No_of_bins=500
list_of_bins=[]
BINS=deque([],maxlen=240)
buffer=[]
dict_averages={}
dict_existing={}
dict_need={}
#dict_materials={"0":"plastic","1":"glass","2","paper"}



class Bins():
    def __init__(self, ID, full, plastic, glass, paper, number):
        self.ID=ID
        self.full=full
        self.number=number
        if number!=0:
            self.plastic=(100*plastic)/number
            self.glass=(100*glass)/number
            self.paper=(100*paper)/number
        else:
            self.plastic=plastic
            self.glass=glass
            self.paper=paper
        
       


def stats(bins_list):
    global dict_averages
    
    length=len(bins_list)
   
    Sum=0
    for j in range(len(bins_list[0])):
            Sum=0
            for i in range(length):
                Sum+=bins_list[i][j].full
            Average=Sum/length
            dict_averages[bins_list[0][j].ID].full=Average

    for j in range(len(bins_list[0])):
            Sum=0
            for i in range(length):
                Sum+=bins_list[i][j].plastic
            Average=Sum/length
            dict_averages[bins_list[0][j].ID].plastic=Average

    for j in range(len(bins_list[0])):
            Sum=0
            for i in range(length):
                Sum+=bins_list[i][j].glass
            Average=Sum/length
            dict_averages[bins_list[0][j].ID].glass=Average

    for j in range(len(bins_list[0])):
            Sum=0
            for i in range(length):
                Sum+=bins_list[i][j].paper
            Average=Sum/length
            dict_averages[bins_list[0][j].ID].paper=Average

    for j in range(len(bins_list[0])):
            Sum=0
            for i in range(length):
                Sum+=bins_list[i][j].number
            Average=Sum/length
            dict_averages[bins_list[0][j].ID].number=Average

    


def read():
    
    global BINS
    global list_of_bins
    buffer=[]
    list_of_bins=[]
    script_dir = os.path.dirname(__file__)
    file_path = os.path.join(script_dir, 'Input.txt')
    file_read = open(file_path, "r")

    

    Time=(datetime.utcnow() - datetime(1970, 1, 1)).total_seconds()
    Time/=60
    Time=int(Time)
    if Time % 1==0:
        
        for line in file_read:
            buffer=[]
           
            for i in line.strip().split():
                buffer.append(i)
          
            ID=int(buffer[0])
            
            full=int(buffer[1])
            plastic=int(buffer[2])
            glass=int(buffer[3])
            paper=int(buffer[4])
            number=int(buffer[5])
            
            bin_temporary=Bins(ID,full,plastic,glass,paper,number)
            list_of_bins.append(bin_temporary)
          
            
      
        BINS.append(list_of_bins)
       
    return BINS

def need(dict_need):
    global dict_averages
    global BINS
    Answer=[]
    for j in range(len(BINS[0])):
        for i in range(3):
            if dict_need[BINS[0][j].ID][i]==1:
                ID=BINS[0][j].ID
                if i==0:
                    material="plastic"
                    percentage=dict_averages[BINS[0][j].ID].plastic
                    
                if i==1:
                    material="glass"
                    percentage=dict_averages[BINS[0][j].ID].glass
                if i==2:
                    material="paper"
                    percentage=dict_averages[BINS[0][j].ID].paper
                Answer.append([ID, material, percentage])
                
    return(Answer)

def always_running_thread():
    
    global BINS
    while(True):
        
        
        BINS=read()
        
       
        time.sleep(10)
       
       
        

def start_statistics(threshold=40):
    global Flag
    global BINS
    script_dir = os.path.dirname(__file__)
    file_path = os.path.join(script_dir, 'Output.txt')
    file_write = open(file_path, "w+")

    
    
    for j in range(len(BINS[0])):
        dict_averages[BINS[0][j].ID]=Bins(BINS[0][j].ID,0,0,0,0,0)
        if Flag==0:
            dict_existing[BINS[0][j].ID]=[0,0,0]
            dict_need[BINS[0][j].ID]=[0,0,0]
    Flag=1
        
    stats(BINS)
    
       
    
    for j in range(len(BINS[0])):
        
        if dict_averages[BINS[0][j].ID].plastic> threshold and dict_existing[BINS[0][j].ID][0]==0:
            dict_need[BINS[0][j].ID][0]=1
        else:
            dict_need[BINS[0][j].ID][0]=0
        if dict_averages[BINS[0][j].ID].glass> threshold and dict_existing[BINS[0][j].ID][1]==0:
            dict_need[BINS[0][j].ID][1]=1
        else:
            dict_need[BINS[0][j].ID][1]=0
        if dict_averages[BINS[0][j].ID].paper> threshold and dict_existing[BINS[0][j].ID][2]==0:
            dict_need[BINS[0][j].ID][2]=1
        else:
            dict_need[BINS[0][j].ID][2]=0


    Answer=need(dict_need)
    print("Bins to be added")
    for element in Answer:
        print(element)
    
    for item in Answer:
        for i in range(3):
            
            file_write.write(str(item[i]))
            file_write.write(" ")
        file_write.write("\n")
    
def trigger_stats():
   
    global BINS
    #always_running_thread.join()
    while(True):
        #c.acquire()
        if len(BINS)>0:
            start_statistics()
        

        #c.notify_all()
        #c.release()
        time.sleep(10)


always_running_thread = Thread(target=always_running_thread, args=())       #Reading input data in a thread
always_running_thread.start()

time.sleep(2)

stats = Thread(target=trigger_stats(), args=())    #Running the statistic analysis in a second thread
stats.start()


always_running_thread.join()
stats.join()
  
                
        
        
    
    
    
    



    



    
    
