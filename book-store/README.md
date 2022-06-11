### Dependências projeto
* Spring Web
* Spring Validations
* Spring data JPA
* Spring Security
* Lombok
* MySQL
* Swagger 3

Antes de trabalhar/executar no projeto

* Baixar plugin do lombok acessando `File/Setting`. Na nova janela use a pesquisa e busque pela palavra `plugins`. Na consulta de plugins digite `lombok`. No resultado da pesquisa clique em `Install`.
* Criar o diretório para imagens no root do projeto `images/`. 

### Persistência de arquivos

A aplicação faz a persistência de imagens em um repositório. Por se tratar de um projeto didático optamos por persistir os arquivos localmente. Em um ambiente produtivo esta abordagem geralmente não é a mais adequada. Outras formas devem ser consideradas, como por exemplo:

* Serviços de armazenagem de arquivos na núvem (AWS S3)
* Servidor de arquivos da empresa

`Importante`: Antes de executar o projeto, o diretório para arquivos deve ser criado manualmente, uma vez que, não é responsabilidade da aplicação saber criar o repositório, mas sim utilizar o mesmo para persistir arquivos.

## Banco de dados

Para subir o docker do banco de dados basta acessar  o diretório do projeto e executar o comando `docker-compose up`. Para usar o banco de dados desta maneira, o `docker` e o `docker compose` são uma dependência.
Também é possível subir uma instalação própria do banco de dados sem utilizar `docker`. Neste caso, observe as configurações de porta e nome do banco no arquivo de configuração `application.yml`.

### Acessando o Swagger
* http://localhost:8080/book-store.html

### Usuário e senha
* usuário: bootcamp
* senha: vempratqi