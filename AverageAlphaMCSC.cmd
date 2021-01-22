FOR %%s IN (11 12 17 18 19 20 25 26 27 28 33 34 35 36) DO (
FOR %%t IN (11 13 15) DO (
java -jar MCSC.jar CMLP_%%s.dat %%t
)
)