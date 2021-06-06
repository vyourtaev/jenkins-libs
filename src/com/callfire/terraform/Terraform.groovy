package com.callfire.terraform

terraformEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(Map pipelineParams=[:]) {
    terraformEnv = [
            TERRAFORM_TOOL: 'terraform-0.13.7',
            param1: "value1",
            param2: "value2"
    ]

    terraformEnv += pipelineParams
    def vm_config_file = libraryResource 'com/callfire/terraform/vm_config.json'
    terraformEnv.vm_config = readJSON text: vm_config_file

    node {
        checkout([
                $class: 'GitSCM',
                branches: [[name: params.TERRAFORM_BRANCH]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [[$class: 'RelativeTargetDirectory',relativeTargetDir: 'terraform']],
                submoduleCfg: [],
                userRemoteConfigs: [[
                   credentialsId: '6115acaa-96d8-485d-b890-1acc47d58788',
                   url: params.TERRAFORM_REPO
                ]]
        ])
    }
}

def getTerraformEnv() {
    return terraformEnv
}

def plan(args) {
    def command = terraformEnv.vm_config + '=====' + env.VAULT_TOKEN
    return sh (script: "echo $command", returnStdout: true)
}

def apply(args) {
    def command = "terraform apply $args - $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def destroy(args) {
    def command = "terraform destroy $args - $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}
