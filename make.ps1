
$currentPath = $pwd

Invoke-Expression -Command:"mvn -f pom.xml clean package -U"

$projs=@("reinforcement-learning-flappy-bird")
foreach ($proj in $projs){
    $source=$PSScriptRoot + "/target/" + $proj + ".jar"
    $dest=$PSScriptRoot + "/bin/" + $proj + ".jar"
    copy $source $dest
}

cd $currentPath
