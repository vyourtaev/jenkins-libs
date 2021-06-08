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
            dynamic_stages_path: 'terraform/stage/stage-dynamic'
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

        terraformEnv.terraform_bin = tool(terraformEnv.TERRAFORM_TOOL) + '/terraform'
    }
}

def getTerraformEnv() {
    return terraformEnv
}

def plan(args) {
    def command = terraformEnv.terraform_bin
    return sh (script: "${args}", returnStdout: true)
}

def apply(args) {
    def command = "terra apply $args.name $terraformEnv.vm_config"
    return sh (script: "echo $command", returnStdout: false)
}

def destroy(args) {
    def command = "terraform destroy $args - $terraformEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def workspace_list() {
    return exec_command("workspace list")
}

def workspace_init() {
    dir(terraformEnv.dynamic_stages_path) {
        return exec_command("init -upgrade")
    }
}

def exec_command(String args) {
    def command = terraformEnv.terraform_bin
    return sh (script: "${command} $args")
}