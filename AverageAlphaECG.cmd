FOR %%s IN (01 02 03 04 09 10 11 12 17 18 19 20 25 26 27 28 33 34 35 36) DO (
FOR %%t IN (11 13 15) DO (
java -jar ECG.jar CMLP_%%s.dat %%t
)
)