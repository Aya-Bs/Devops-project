stage('Docker Compose Up') {
    steps {
        script {
            echo "🚀 Lancement des conteneurs avec Docker Compose..."
            try {
                sh """
                    docker-compose down --remove-orphans || true
                    docker ps -a | grep kaddem | awk '{print \$1}' | xargs -r docker rm -f
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