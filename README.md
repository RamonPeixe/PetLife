# Especificação de Exercício 📝
## PetLife 🐾
**Ramon César Peixe**  
SC3034992

### 1. Objetivo 🎯
Avaliar os conhecimentos obtidos com os conceitos nas disciplinas relacionadas ao desenvolvimento para dispositivos móveis.

### 2. Restrições 🚫
- **Nome do projeto:** O nome do projeto e, consequentemente, do repositório Git compartilhado deve ser **PetLife**. Respeite letras maiúsculas e minúsculas. Qualquer diferença em relação ao nome do repositório dado implica em não correção do projeto.

### 3. Descrição 📱
O exercício consiste no desenvolvimento de um aplicativo Android para gerenciar e exibir informações sobre o pet de uma pessoa, incluindo a última ida ao veterinário, a última vacinação e a última ida ao petshop. Os dados devem ser temporários, armazenados em memória e reiniciados quando o aplicativo for fechado e reaberto.

#### 3.1. Funcionalidades:
- **Tela Principal (dashboard do pet):**
  - Exibe as informações básicas do pet: Nome, data de nascimento, tipo (cão ou gato), cor, porte (pequeno, médio ou grande).
  - Exibe as datas da última ida: ao veterinário, ao petshop e para vacinação.
  - Deve haver um menu com opções para alterar os dados: do pet, da última ida ao veterinário, da última vacinação. Cada uma das opções deve abrir uma nova tela para edição específica.
  - Os dados anteriores à edição devem estar preenchidos em cada um dos campos.
  - Em cada uma das telas de edição deve haver um botão "Salvar" que, ao ser clicado, fecha a tela atual. Os novos dados salvos devem ser atualizados na tela principal.

#### 3.2. Requisitos:
- **Interface simples e intuitiva:** Usar layouts básicos (ConstraintLayout, LinearLayout) para organizar os componentes.
- **Armazenamento temporário:** Não será utilizado nenhum banco de dados ou armazenamento permanente. Todos os dados devem ser armazenados em memória (por exemplo, em variáveis ou objetos) e serão reiniciados quando o aplicativo for reiniciado.
- **Uso de Intents:** Utilizar Intents para navegação entre as telas.
- Considere todas as datas como atributos do tipo String. Use pelo menos uma classe `Pet` para agregar todos os dados que serão exibidos. Classes auxiliares podem ser criadas.
- O uso de **conventional commits** é mandatório.

### 4. Entrega 📦
A entrega deve ser feita por meio do compartilhamento do repositório Git até a data estipulada no ambiente da disciplina. Além do código do projeto, o repositório deve conter um pequeno vídeo que demonstre o aplicativo sendo executado.

### 5. Correção ✅
O exercício será avaliado em duas oportunidades que comporão a nota da disciplina:
- A primeira avaliação consiste no desenvolvimento das interfaces de usuário e da lógica inicial do aplicativo para as funcionalidades solicitadas.
- A segunda avaliação consiste na avaliação do aplicativo completo com o requisito extra que será solicitado em sala, em data previamente divulgada. São obrigatórios o comparecimento na data e a implementação do requisito extra. O não comparecimento ou a não implementação do requisito implica em invalidação da entrega.

**Observação:** Use somente as técnicas vistas em sala. Caso queira usar algum recurso além daqueles vistos em sala, converse com o professor antes do uso. Concentre-se no desenvolvimento das funcionalidades do aplicativo. Apesar de interessante, a beleza do aplicativo não é considerada na correção, mas apenas a funcionalidade.

---

### Estrutura do Projeto 📂

#### PetLife/app/src/main/java/br/edu/ifsp/scl/ads/petlife:
- **EdiPetActivity.kt** (responsável por editar dados do pet)
- **EditPetShopActivity.kt** (responsável por editar a última ida ao petshop do pet)
- **EditVacinaActivity.kt** (responsável por editar a data da última vacinação do pet)
- **EditVeterinarioActivity.kt** (responsável por editar a data da última visita ao veterinário do pet)
- **MainActivity.kt** (responsável por exibir os dados e chamar as outras intents para edição)
- **Pet.kt** (classe pet)

#### PetLife/app/src/main/res/layout:
- **activity_edit_pet.xml** (tela para editar os dados do pet)
- **activity_edit_petshop.xml** (tela para editar a última ida ao petshop)
- **activity_edit_vacina.xml** (tela para editar a data da última vacinação)
- **activity_edit_veterinario.xml** (tela para editar a data da última ida ao veterinário)
- **activity_main.xml** (tela principal com dashboard com as informações do pet e os botões para edição)

### Observações Adicionais 🛠️
- 🍪 O projeto foi desenvolvido na API 26 (Android 8.0 Oreo).
- 📹Link do vídeo mostrando a execução do projeto: https://youtu.be/iNpYqRcx2y0 
