function displayResults(persons) {
    const container = document.getElementById('results-container');
    container.innerHTML = '';

    if (persons.length === 0) {
        container.innerHTML = '<p class="no-results">No matching persons found. Try different search criteria.</p>';
        return;
    }

    persons.forEach(person => {
        const card = document.createElement('div');
        card.className = 'result-card';

        // Convert byte array to base64 for image display
        const base64String = btoa(String.fromCharCode(...new Uint8Array(person.photo)));

        card.innerHTML = `
            <div class="person-photo">
                <img src="data:image/jpeg;base64,${base64String}" alt="${person.name}">
            </div>
            <div class="person-details">
                <h3>${person.name}</h3>
                <p><strong>Physical Description:</strong>
                   ${person.height}cm, ${person.eyeColor} eyes, ${person.hairColor} hair</p>
                <p><strong>Last Seen:</strong> ${person.lastSeenLocation}</p>
                <p><strong>Contact:</strong> ${person.contactInfo}</p>
            </div>
        `;

        container.appendChild(card);
    });
}
