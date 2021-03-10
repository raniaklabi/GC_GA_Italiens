FOR %%s IN (01 02 03 04 09 10 11 12 ) DO (
FOR %%t IN (0.7 0.85 1) DO (
java -jar MCSCBeta.jar CMLP_%%s.dat %%t
)
)