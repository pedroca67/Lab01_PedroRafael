@echo off
echo ===============================
echo   RESETANDO O BANCO DE DADOS...
echo ===============================

java -cp build;lib/* ResetDatabase

echo.
echo === BANCO PRONTO PARA PROXIMO TESTE ===