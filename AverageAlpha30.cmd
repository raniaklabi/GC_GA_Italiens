FOR %%s IN (40) DO (
FOR %%t IN (0.7 0.85 1) DO (
java -jar HCG.jar CMLP_%%s.dat %%t
)
)