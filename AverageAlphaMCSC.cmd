FOR %%s IN (28 33 34 35 36) DO (
FOR %%t IN (11 13 15) DO (
java -jar MCSC.jar CMLP_%%s.dat %%t
)
)