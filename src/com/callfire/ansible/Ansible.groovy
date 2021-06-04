package com.callfire.ansible

ansibleEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(pipelineParams) {
//    ansibleEnv = [
//            args: args,
//            inventory: 'inventory/hosts_cf_stage',
//            subset: 'vault',
//            pattern: 'vault'
//    ]
    ansibleEnv = pipelineParams
}

def getAnsibleEnv() {
    return ansibleEnv
}

def play(String args) {
    def command = "ansible $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}

def playbook(String args) {
    def command = "ansible-plabook $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}
