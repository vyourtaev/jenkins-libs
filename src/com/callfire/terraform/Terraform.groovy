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

//    terraformEnv.vm_count = terraformEnv.vm_config.vm_count + terraformEnv.vm_config.watson_vars
//    terraformEnv.vars = terraformEnv.vm_count.collect({k, v -> { "-vars $k=$v"}}.join(' '))

    terraformEnv.state_path = "${env.WORKSPACE}/../terraform-state"

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

def destroy(args) {
    def command = "echo plan -v env_name=ENV_NAME" +
                  "-state=$terraformEnv.state_path/ENV_NAME" +
                  "-parallelism=25" +
                  "-auto-approve" +
                  "-input=false" +
                  "$terraformEnv.vm_config"
    return sh (script: "$command", returnStdout: false)
}

def workspace_list() {
    return exec_command("workspace list")
}

def workspace_init() {
    dir(terraformEnv.dynamic_stages_path) {
        return exec_command("init -upgrade")
    }
}

def apply(args) {
    dir(terraformEnv.dynamic_stages_path) {
        return exec_command("""
            plan 
                -v env_name=$args.name 
                -var labels_custom={ user = 'ci' } 
                -state="$terraformEnv.state_path/$name" 
                -parallelism=25
                -auto-approve
                -input=false
                $terraformEnv.vm_count
        """)
    }
}

def exec_command(String args) {
    def command = terraformEnv.terraform_bin
    return sh (script: "${command} $args", returnStdout: false)
}