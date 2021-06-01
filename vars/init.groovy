def call(String name = "Dynamic default env name") {
    script {
        sh """
            echo Hi ${name}
        """
    }
}
