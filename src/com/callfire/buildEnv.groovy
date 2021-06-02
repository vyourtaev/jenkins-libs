package com.callfire

def adhoc ( String inventory, String pattern, String module, String args ) {
  return sh (script: "echo ansible -i $inventory $pattern -m $module -a '$args'", returnStdout: true)
}

def ansible(String inventory, String limit, String pattern ) {
  return sh (script: "echo ansilbe -i $inventory -l $limit $pattern", returnStdout: true)
}

def terraform(String action) {
  return sh (script: "echo terraform plan", returnStdout: true)
}


