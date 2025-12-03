
document.addEventListener('DOMContentLoaded', function () {

    const tagsContainer = document.getElementById('tags-container');
    const tagInput = document.getElementById('tag-input');

    let tags = [];

    const suggestionsList = document.getElementById('suggestions-list');

    const availableSuggestions = suggestions;

    function createTag(label) {
        const li = document.createElement('li');

        li.className = 'tag';
        li.textContent = label;

        const removeBtn = document.createElement('button');
        removeBtn.className = 'remove-btn';
        removeBtn.textContent = 'x';

        removeBtn.addEventListener('click', () => {
            removeTag(li, label);
        });

        li.appendChild(removeBtn);
        tagsContainer.appendChild(li);

    }

    function showSuggestions() {
        suggestionsList.innerHTML = '';
        const query = tagInput.value.trim().toLowerCase();

        if (query.length === 0) {
            suggestionsList.style.display = 'none';
            return;
        }

        const filteredSuggestions = availableSuggestions.filter(suggestion =>
            suggestion.toLowerCase().includes(query) &&
            !tags.includes(suggestion)
        );

        if (filteredSuggestions.length > 0) {
            filteredSuggestions.forEach(suggestion => {
                const li = document.createElement('li');
                li.textContent = suggestion;
                li.className = 'suggestion-item';

                // Ouvinte de evento: ao clicar, adiciona a tag e limpa o input
                li.addEventListener('click', () => {
                    // Adiciona a tag usando a lógica já existente
                    tags.push(suggestion);
                    createTag(suggestion);

                    tagInput.value = ''; // Limpa o input
                    showSuggestions(); // Oculta a lista de sugestões
                    tagInput.focus(); // Retorna o foco para continuar digitando
                });

                suggestionsList.appendChild(li);
            });
            suggestionsList.style.display = 'block';
        } else {
            suggestionsList.style.display = 'none';
        }
    }

    function removeTag(tagElement, label) {
        tagsContainer.removeChild(tagElement);

        const index = tags.indexOf(label);

        if (index > -1) {
            tags.splice(index, 1);
        }

        console.log('Tags atuais: ', tags);
    }

    tagInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' || e.key === ',') {
            e.preventDefault();

            const tagLabel = tagInput.value.trim();

            // NOVO: Verifica se a tag existe na lista de sugestões
            if (tagLabel && 
                !tags.includes(tagLabel) && 
                availableSuggestions.includes(tagLabel) // <--- ESTA É A MUDANÇA
            ) {
                tags.push(tagLabel);
                createTag(tagLabel);

                tagInput.value = '';
                showSuggestions(); // Opcional: Atualiza a lista após adicionar
            }
        }
    });

    tagInput.addEventListener('input', showSuggestions);

    tagInput.addEventListener('blur', () => {
        setTimeout(() => {
            const tagLabel = tagInput.value.trim();
            
            if (tagLabel && 
                !tags.includes(tagLabel) &&
                availableSuggestions.includes(tagLabel) // <--- ADICIONE TAMBÉM AQUI
            ) {
                tags.push(tagLabel);
                createTag(tagLabel);
                tagInput.value = '';
            }
            
            suggestionsList.style.display = 'none';
        }, 150);
    });

    tagInput.addEventListener('focus', () => {
        showSuggestions();
    });
});

