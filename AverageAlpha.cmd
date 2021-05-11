FOR %%s IN (11) DO (
FOR %%t IN (0.7 0.85) DO (
java -jar HCG.jar CMLP_%%s.dat %%t
)
)