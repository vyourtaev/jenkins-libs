package com.callfire.terraform

terraformEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(Map pipelineParams=[:]) {
    terraformEnv = [
            TERRAFORM_TOOL: 'terraform-0.14.11',
            dynamic_stages_path: 'terraform/stage/stage-dynamic',
            state_path: 1
     ]

    terraformEnv += pipelineParams

    def vm_config_file = libraryResource 'com/callfire/terraform/vm_config.json'

    terraformEnv.vm_config = readJSON text: vm_config_file

    terraformEnv.vm_count = terraformEnv.vm_config.vm_count + terraformEnv.vm_config.watson_vars
    terraformEnv.vars = getTerraformVars(terraformEnv.vm_count)

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

def getTerraformVars(vars) {
    return vars.collect {k,v -> "-var $k=$v"}.join(' ')
}

def getEnvironmentVariables() {
    return sh (script: "echo $env.WORKSPACE", returnStdout: false)
}


def destroy(args) {
    def command = "echo plan -v env_name=$args.name " +
                  "-state=$env.WORKSPACE/../terraform-state/$args.name " +
                  "-parallelism=25 " +
                  "-auto-approve " +
                  "-input=false " +
                  "$terraformEnv.vars"
    return sh (script: "$command", returnStdout: false)
}

def workspace_list() {
    return exec_command("workspace list")
}

def workspace_init() {
    return exec_command(('init -upgrade'))
}

def plan(args) {
        return exec_command("plan " +
                "-var env_name=$args.name " +
                "-state=$env.WORKSPACE/../terraform-state/$args.name/terraform.tfstate " +
//                "-var labels_custom={ user = 'ci' } " +
                "-parallelism=25 " +
                "-input=false " +
                "$terraformEnv.vars")
}

def apply(args) {
    return exec_command("plan " +
            "-var env_name=$args.name " +
            "-state=$env.WORKSPACE/../terraform-state/$args.name/terraform.tfstate " +
//                "-var labels_custom={ user = 'ci' } " +
            "-parallelism=25 " +
            "-input=false " +
            "$terraformEnv.vars")
}

def exec_command(String args) {
    def command = terraformEnv.terraform_bin

    dir('terraform/stage/stage-dynamic') {
        return sh(script: "${command} $args", returnStdout: false)
    }
}