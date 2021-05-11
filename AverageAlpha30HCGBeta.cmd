FOR %%s IN (05 06 07 08 13 14 15 16) DO (
FOR %%t IN (0.7 0.85 1) DO (
java -jar HCGBeta.jar CMLP_%%s.dat %%t
)
)