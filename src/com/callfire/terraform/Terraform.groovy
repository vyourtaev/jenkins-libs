package com.callfire.terraform

terraformEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(Map pipelineParams=[:]) {
    terraformEnv = [
            param1: "value1",
            param2: "value2"
    ]

    terraformEnv += pipelineParams
    def file = libraryResource 'vm_config.json'

//    node {
//        checkout([
//                $class: 'GitSCM',
//                branches: [[name: params.TERRAFORM_BRANCH]],
//                doGenerateSubmoduleConfigurations: false,
//                extensions: [[$class: 'RelativeTargetDirectory',relativeTargetDir: 'terraform']],
//                submoduleCfg: [],
//                userRemoteConfigs: [[
//                   credentialsId: '6115acaa-96d8-485d-b890-1acc47d58788',
//                   url: params.TERRAFORM_REPO
//                ]]
//        ])
//    }
}

def getTerraformEnv() {
    return terraformEnv
}

def plan(args) {
    def command = "$file"
    return sh (script: "echo $command | jq ", returnStdout: true)
}

def apply(args) {
    def command = "terraform apply $args - $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def destroy(args) {
    def command = "terraform destroy $args - $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}
