package com.callfire

buildEnv

ansibleHandler

/**
 * Imitates a constructor with object composition
 * Defines an instance of Buildenv object.
 * @param ansibleObj - an Ansible object
 * @return
 */
def construct(ansibleObj){
  buildEnv = [:]
  ansibleHandler = ansibleObj
}

def getBuildEnv() {
  return buildEnv
}

def test() {
  return ansibleHandler.play("Test case")
}

def adhoc ( String inventory, String pattern, String module, String args ) {
  return sh (script: "echo ansible -i $inventory $pattern -m $module -a '$args'", returnStdout: true)
}

def ansible(String inventory, String limit, String pattern ) {
  return sh (script: "echo ansilbe -i $inventory -l $limit $pattern", returnStdout: true)
}

def terraform(String action) {
  return sh (script: "echo terraform $action", returnStdout: true)
}


