@echo off
echo Iniciando Teste da Parte 1 (Produto/Estoque)...

echo Iniciando ProdutoWriter...
start cmd /k "java -cp build;lib/* ProdutoWriter"

:: Aguarda 2 segundos para o Writer iniciar
timeout /t 2 >nul

echo Iniciando ProdutoReader...
start cmd /k "java -cp build;lib/* ProdutoReader"