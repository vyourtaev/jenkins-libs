def call(String args){
    def terraform =  new com.callfire.terraform.Terraform()
    terraform.construct(args)
    return terraform
}
