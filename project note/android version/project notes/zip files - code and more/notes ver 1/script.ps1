$logPath = "decode_log.txt"

try {
    # 1. Check if the source file exists
    if (-not (Test-Path "encoded.txt")) {
        throw "Source file 'encoded.txt' was not found in the current folder."
    }

    # 2. Read the raw text from the file
    $base64 = Get-Content encoded.txt -Raw

    # 3. Keep ONLY valid Base64 characters (letters, numbers, +, /, and =)
    # This strips hidden formatting, quotes, carriage returns, or invisible trailing HTML spaces.
    $base64Cleaned = $base64 -replace '[^A-Za-z0-9+/=]'

    # 4. Dynamically fix Base64 padding issues
    $mod = $base64Cleaned.Length % 4
    if ($mod -eq 2) {
        $base64Cleaned += "=="
    } elseif ($mod -eq 3) {
        $base64Cleaned += "="
    } elseif ($mod -eq 1) {
        # Mathematically, Base64 strings cannot have a remainder of 1.
        # This means a trailing character is corrupted or missing; we trim it to avoid failure.
        $base64Cleaned = $base64Cleaned.Substring(0, $base64Cleaned.Length - 1)
        Write-Warning "The Base64 string length was invalid (remainder of 1). One trailing character was trimmed to allow parsing."
    }

    # 5. Decode the cleaned and self-padded string
    $bytes = [System.Convert]::FromBase64String($base64Cleaned)

    # 6. Write the binary output directly to updated_code.zip
    [System.IO.File]::WriteAllBytes("updated_code.zip", $bytes)

    # 7. Generate Success Log
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $successMsg = "[$timestamp] SUCCESS: 'encoded.txt' decoded successfully. Total Base64 characters parsed: $($base64Cleaned.Length). 'updated_code.zip' created."
    Set-Content -Path $logPath -Value $successMsg
    
    Write-Host $successMsg -ForegroundColor Green
}
catch {
    # 8. Generate Error Log if execution fails
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $errorMsg = "[$timestamp] ERROR: Failed to process encoding. Details: $_"
    Set-Content -Path $logPath -Value $errorMsg
    
    Write-Error "An error occurred during execution. Please check '$logPath' for more details."
}