### Dependências projeto
* Spring Web
* Spring Validations
* Spring data JPA
* Spring Security
* Lombok
* MySQL

Antes de trabalhar/executar no projeto

* Baixar plugin do lombok acessando `File/Setting`. Na nova janela use a pesquisa e busque pela palavra `plugins`. Na consulta de plugins digite `lombok`. No resultado da pesquisa clique em `Install`.
* Criar o diretório para imagens `/tmp/book_store/images`
### Persistência de arquivos

Por se tratar de um projeto didático optamos por persistir os arquivos localmente. Em um ambiente produtivo esta abordagem geralmente não é a mais adequada. Outras formas devem ser consideradas, como por exemplo:

* Serviços de armazenagem de arquivos na núvem (AWS S3)
* Servidor de arquivos da empresa