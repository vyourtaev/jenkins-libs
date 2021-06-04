def call(pipelineParams){
    def terraform =  new com.callfire.terraform.Terraform()
    terraform.construct {
        pipelineParams
    }
    return terraform
}