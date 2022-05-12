# **FLAPDA**

## **Objetivo**

- Implementar um simulador de autômatos com pilha – ACP, que permita ao usuário testar o reconhecimento de diversas cadeias para qualquer ACP de entrada.

## **Requisitos**

- O programa deverá permitir que o usuário introduza qualquer ACP, ou seja, para montar o autômato A = (Q, Σ, Γ, δ, q0, Z0, F), o usuário deverá informar:
  - Os estados do Autômato
  - O alfabeto do Autômato
  - O alfabeto da pilha
  - A função de transição
  - O estado inicial
  - O símbolo de início da pilha
  - Os estados de aceitação
- Como forma de simplificação, é permitida a restrição do alfabeto a apenas 2 símbolos.
- O programa deverá rejeitar autômatos inválidos

## **Manual de Uso**

1. Modificar o arquivo `"automaton.json"` de acordo com a configuração do autômato desejado.
2. Modificar o arquivo `"transition.csv"` seguindo os seguintes parâmetros:

- \<estado atual>,\<topo da pilha>,\<entrada>,\<próximo estado>,\<string a ser escrita na pilha>
- Observações:
  - Símbolos repetidos nos alfabetos serão ignorados
  - Para cada par do conjunto resultante da função de transição é preciso fazer uma nova linha no arquivo `.csv`
  - O vazio ( ε ) é simbolizado com a ausência de um caractere (mantendo a mesma quantidade de vírgulas).
- Exemplificação:

      δ(q0, 0, Z) = { (q1, 0Z) }
      δ(q0, ε, Z) = { (q1, 0Z), (q0, ε) }

      👉

      q0,0,Z,q1,0Z
      q0,,Z,q1,0Z
      q0,,Z,q0,

### Discentes:

- Guilherme Ricardo Araújo Melo
- João Artur Silva Moura
- Joshua Martins Duarte
