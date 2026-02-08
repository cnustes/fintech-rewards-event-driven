$src = "J:\LocalDevelopments\bilt-rewards-system"
$dst = "D:\LocalDevelopment\fintech-rewards-event-driven"

Write-Host "Creating destination..."
if (!(Test-Path $dst)) {
    New-Item -ItemType Directory -Force -Path $dst | Out-Null
}

Write-Host "Copying files..."
# Exclude system folders and build artifacts
Get-ChildItem -Path $src -Exclude ".git", ".idea", "target", ".mvn", ".vscode" | Copy-Item -Destination $dst -Recurse -Force

Write-Host "Renaming package directories..."
# Only rename 'bilt' folders that are part of the package structure
Get-ChildItem -Path $dst -Recurse -Directory | Where-Object { $_.Name -eq "bilt" } | ForEach-Object {
    if ($_.FullName -match "com\\camilonustes\\bilt") {
        Write-Host "Renaming $($_.FullName) to fintech"
        Rename-Item -Path $_.FullName -NewName "fintech"
    }
}

Write-Host "Replacing content in files..."
$files = Get-ChildItem -Path $dst -Recurse -File
foreach ($file in $files) {
    if ($file.Extension -match "\.(java|xml|yml|yaml|md|properties|txt)$") {
        $content = Get-Content -Path $file.FullName -Raw
        $original = $content
        
        # 1. Project Name & Artifacts
        $content = $content -replace "bilt-rewards-system", "fintech-rewards-event-driven"
        
        # 2. Package Names
        $content = $content -replace "com.camilonustes.bilt", "com.camilonustes.fintech"
        
        # 3. Metrics & Configs
        $content = $content -replace "bilt\.rewards", "fintech.rewards"
        $content = $content -replace "bilt_password", "fintech_password"
        
        # 4. Text & Documentation
        $content = $content -replace "(?i)Bilt Rewards System", "Fintech Rewards System"
        $content = $content -replace "Bilt", "Fintech" # Careful with this one
        
        # 5. Docker Containers prefix
        $content = $content -replace "bilt-", "fintech-"

        if ($content -ne $original) {
            Write-Host "Updating $($file.Name)"
            Set-Content -Path $file.FullName -Value $content -NoNewline -Encoding UTF8
        }
    }
}

Write-Host "Migration complete. New location: $dst"
