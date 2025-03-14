stage('Docker Compose Up') {
    steps {
        script {
            echo "🚀 Lancement des conteneurs avec Docker Compose..."
            try {
                sh """
                    docker-compose down || true
                    docker-compose up -d
                """
            } catch (Exception e) {
                echo "⚠️ Attention: Problème lors du démarrage des conteneurs Docker Compose: ${e.message}"
                // On ne fait pas échouer le pipeline, on continue
                unstable('Docker Compose a rencontré des problèmes mais le pipeline continue')
            }
        }
    }
} 