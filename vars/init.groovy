def call(String name = "Dynamic Stage env") {
    script {
        sh """
            echo Hi ${name}
        """
    }
}
