# Regras de Desenvolvimento

Para garantir um fluxo de trabalho organizado e evitar conflitos no código, siga as regras abaixo ao desenvolver novas funcionalidades ou correções:

## Pull antes de começar

* Sempre faça um `pull` da branch `develop` antes de iniciar qualquer desenvolvimento.

## Criação de branches

* Sempre crie uma nova branch a partir da `develop`.
* O nome da branch deve seguir o padrão: `feature|fix|hotfix/nome-da-branch`
    * Exemplos:
        * `feature/adicionar-filtro-busca`
        * `fix/corrigir-bug-layout`
        * `hotfix/ajuste-urgente-login`

## Pull Request (PR)

* Após concluir o desenvolvimento, abra um  Pull Request (PR) da sua branch para a `develop`.
* O código deve ser testado na branch `develop` antes de seguir para a `main`.

## Testes e Aprovação
Antes de integrar qualquer mudança na `main`, garanta que todos os testes foram executados e nenhuma funcionalidade está quebrando!

## Pull Request para `main`
Após a aprovação na develop, abra um PR da `develop` para a `main`.
Esse processo garante que a branch principal sempre contenha apenas código testado e estável.

#
💡 Seguindo essas regras, mantemos um fluxo de trabalho eficiente e organizado! 🚀
