FOR %%s IN (03 04 05 06 07 08 09 10 11 12 13 14 15 16) DO (
FOR %%t IN (0.7 0.85 1) DO (
java -jar MCSCExateBeta.jar CMLP_%%s.dat %%t
)
)