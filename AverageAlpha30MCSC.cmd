FOR %%s IN (05 06 07 08 13 14 15 16 21 22 23 24 29 30 31 32 37 38 39 40) DO (
FOR %%t IN (0.7 0.85 1) DO (
java -jar MCSC.jar CMLP_%%s.dat %%t
)
)