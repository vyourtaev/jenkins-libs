package com.callfire.terraform

terraformEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(pipelineParams) {
//    terraformEnv = [
//            param1: "value1",
//            param2: "value2"
//    ]
   terraformEnv = pipelineParams
}

def getTerraformEnv() {
    return terraformEnv
}

def plan(String args) {
    def command = "terraform plan $args $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def apply(String args) {
    def command = "terraform apply $args $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def destroy(String args) {
    def command = "terraform destroy $args $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}
