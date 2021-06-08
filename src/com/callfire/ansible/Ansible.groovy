package com.callfire.ansible

ansibleEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(Map pipelineParams=[:]) {
    ansibleEnv = [
        ANSIBLE_VERSION: '2.8.4'
    ]
    ansibleEnv += pipelineParams

    node {
        checkout([
                $class: 'GitSCM',
                branches: [[name: params.ANSIBLE_BRANCH]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [],
                submoduleCfg: [],
                userRemoteConfigs: [[
                   credentialsId: '6115acaa-96d8-485d-b890-1acc47d58788',
                   url: params.ANSIBLE_REPO
                ]]
        ])
    }
}

def getAnsibleEnv() {
    return ansibleEnv
}

def play(args) {
    def command = "ansible $args - $ansibleEnv "
    return sh (script: "echo $command", returnStdout: true)
}

def playbook(args) {
    def command = "ansible-plabook $args - $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}
