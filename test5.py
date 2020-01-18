from threading import Thread
from threading import Condition
import time
BINS=0
def f1():
    global BINS
    while(True):
        BINS=20
        time.sleep(10)
        BINS=f3()
        time.sleep(10)

def f2():
    global BINS
    while(True):
        time.sleep(3)
        #BINS+=25
        print(BINS)

def f3():
    global BINS
    BINS=100
    return BINS



t1=Thread(target=f1, args=())

t2=Thread(target=f2, args=())

t1.start()
t2.start()

t1.join()
t2.join()
