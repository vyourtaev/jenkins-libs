package com.callfire.ansible

ansibleEnv

/**
 * Constructor. Should be used at the beginning of any usage
 * @param
 * @return
 */
def construct(String args) {
    ansibleEnv = [
            args: args,
            inventory: 'inventory/hosts_cf_stage',
            subset: 'vault',
            patern: 'vault'
    ]
}

def getAnsibleEnv() {
    return ansibleEnv
}

def play(String args) {
    def command = "ansible $args $ansibleEnv"
    return sh (script: "echo $command", returnStdout: true)
}
