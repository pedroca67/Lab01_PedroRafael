@echo off
echo Iniciando Teste da Parte 1 (Produto/Estoque)...

echo Iniciando ProdutoReader (Leitor)...
start cmd /k "java -cp build;lib/* ProdutoReader"

:: Aguarda 3 segundos (para dar tempo ao Reader de fazer a Leitura 1)
timeout /t 3 >nul

echo Iniciando ProdutoWriter (Escritor)...
start cmd /k "java -cp build;lib/* ProdutoWriter"