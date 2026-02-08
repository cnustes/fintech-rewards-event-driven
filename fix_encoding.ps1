$files = Get-ChildItem -Path . -Include *.java,*.xml,*.yml -Recurse
foreach ($file in $files) {
    if ($file.FullName -notmatch "target") {
        $content = [System.IO.File]::ReadAllText($file.FullName)
        $utf8NoBom = New-Object System.Text.UTF8Encoding($False)
        [System.IO.File]::WriteAllText($file.FullName, $content, $utf8NoBom)
        Write-Host "Fixed encoding for $($file.FullName)"
    }
}
