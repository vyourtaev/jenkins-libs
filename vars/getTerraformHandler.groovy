def call(pipelineParams){
    def terraform =  new com.callfire.terraform.Terraform()
    terraform.construct {
        branch = "develop"
        repo = "terraform.git"
    }
    return terraform
}