import numpy as np
import matplotlib.pyplot as plt

plt.close('all')

Bsyn= float(input('Enter rate of synthesis: '))
k=float(input('Enter clearance rate constant: '))
T=float(input('Enter total time: '))
lini=float(input('Enter initial number of molecules: '))

def birth_death_process(lini, T, k, Bsyn):
    ts=[0]
    Ls=[lini]
    while(ts[-1]<T):
            Bdes= k*Ls[-1]
            rates=Bsyn+Bdes
            deltat= -np.log(np.random.random())/rates  
            ts=ts+[ts[-1]+deltat]
        
            if np.random.random()<(Bsyn/rates):
                Ls= Ls+ [Ls[-1]+1]
            else:
                Ls= Ls+ [Ls[-1]-1]
    return ts, Ls

ts, Ls=birth_death_process(lini,T,k,Bsyn)
                      
fig= plt.figure()
plt.plot(ts, Ls)
plt.suptitle('Birth-Death Process')
plt.xlabel('time, a.u.')
plt.ylabel('number of molecules')
